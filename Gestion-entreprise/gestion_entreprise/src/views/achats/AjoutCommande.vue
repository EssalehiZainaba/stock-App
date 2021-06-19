<template>
  <div class="ajoutCommande">
    <Navbar/>
    <v-container fluid class="my-8 px-16">
        <v-card color="grey lighten-4 ">
            <v-card-title>
                Ajout Commande Fournisseur
            </v-card-title>
            <v-card-text class="pl-10 py4">
                <v-form class="px-3 pr-10" ref="form" lazy-validation v-model="valid">
                    <v-row>
                            <v-col md="7" cols="7" sm="7" lg="7">
                                <v-autocomplete
                                    :items="fournisseurs"
                                    dense
                                    label="Fournisseur"
                                    clearable
                                    v-model="fournisseur"
                                    :rules="[v => !!v || 'Selectionner un fournisseur']"
                                    required
                                ></v-autocomplete>                              
                            </v-col>          
                            <v-spacer></v-spacer>
                            <v-col md="3" cols="5" sm="3" lg="3">                              
                                    <v-img
                                        height="100%"
                                        max-width="170"
                                        :src="imgClick"                                   
                                    ></v-img>                         
                            </v-col>
                            <v-col md="4" cols="12" sm="6" lg="4">
                                <v-menu
                                    ref="menu"
                                    v-model="menu"
                                    :close-on-content-click="false"
                                    :return-value.sync="dateEcheance"
                                    transition="scale-transition"
                                    offset-y
                                    min-width="auto">
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-text-field
                                            v-model="dateEcheance"
                                            label="Date d'échéance"
                                            prepend-icon="mdi-calendar"
                                            readonly
                                            v-bind="attrs"
                                            v-on="on">
                                        </v-text-field>
                                    </template>
                                    <v-date-picker
                                        v-model="dateEcheance"
                                        no-title
                                        scrollable>
                                        <v-spacer></v-spacer>
                                        <v-btn
                                            text
                                            color="primary"
                                            @click="menu = false">
                                            Annuler
                                        </v-btn>
                                        <v-btn
                                            text
                                            color="primary"
                                            @click="$refs.menu.save(dateEcheance)">
                                            OK
                                        </v-btn>
                                    </v-date-picker>
                                </v-menu>
                            </v-col>
                            <v-col md="4" cols="12" sm="6" lg="4">
                                <v-menu
                                    ref="menu2"
                                    v-model="menu2"
                                    :close-on-content-click="false"
                                    :return-value.sync="dateReception"
                                    transition="scale-transition"
                                    offset-y
                                    min-width="auto">
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-text-field
                                            v-model="dateReception"
                                            label="Date de réception"
                                            prepend-icon="mdi-calendar"
                                            readonly
                                            v-bind="attrs"
                                            v-on="on">
                                        </v-text-field>
                                    </template>
                                    <v-date-picker
                                        v-model="dateReception"
                                        no-title
                                        scrollable>
                                        <v-spacer></v-spacer>
                                        <v-btn
                                            text
                                            color="primary"
                                            @click="menu2 = false">
                                            Annuler
                                        </v-btn>
                                        <v-btn
                                            text
                                            color="primary"
                                            @click="$refs.menu2.save(dateReception)">
                                            OK
                                        </v-btn>
                                    </v-date-picker>
                                </v-menu>
                            </v-col>
                            <v-col md="4" cols="7" sm="7" lg="4"></v-col>
                            <v-col md="4" cols="12" sm="7" lg="4" class="mt-4">
                                <v-autocomplete
                                    :items="produits"
                                    dense
                                    label="Produit"
                                    clearable
                                    v-model="produit"
                                    :rules="[v => !!v || 'Ajouter un produit']"
                                    required
                                ></v-autocomplete>                              
                            </v-col>
                            <v-col md="4" cols="12" sm="5" lg="3">
                                <v-text-field label="Quantité" clearable v-model="quantite" :rules="quantiteRules"></v-text-field>
                            </v-col>
                            <v-col md="4" cols="9" sm="5" lg="3">
                                <v-text-field label="Prix" clearable v-model="prix" :rules="prixRules"></v-text-field>
                            </v-col>
                            <v-btn text large icon color="green" @click="ajouterProduit" :disabled="!valid" class="mt-6 ml-6">
                                <v-icon>mdi-check-outline</v-icon>
                            </v-btn>
                            <v-col md="12" cols="12" sm="12" lg="12">
                                <div v-if="reveal">                        
                                    <v-simple-table
                                        fixed-header
                                        class="grey lighten-4"
                                        height="200px">
                                        <template v-slot:default>
                                            <thead>
                                                <tr>
                                                    <th class="text-left">
                                                        Article
                                                    </th>
                                                    <th class="text-left">
                                                        Quantité commandée
                                                    </th>
                                                    <th class="text-left">
                                                        Prix unitaire
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr
                                                v-for="produit in produitsTable"
                                                :key="produit.nom"
                                                >
                                                <td>{{ produit.nom }}</td>
                                                <td>{{ produit.quantite }}</td>
                                                <td>{{ produit.prix }}</td>                                               
                                                </tr>
                                            </tbody>
                                        </template>
                                    </v-simple-table>                               
                                </div>
                            </v-col>
                            <v-col md="12" cols="12" sm="12" lg="12">
                                <v-textarea label="description" v-model="description" clearable rows="1" row-height="50"></v-textarea>
                            </v-col>
                    </v-row>                
                    <v-btn text class="primary text mx-0 mt-3"  @click="ajouterCommande" :disabled="!(fournisseur && produitsTable.length)">Ajouter</v-btn>                
                    
                    <v-tooltip bottom > 
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn icon large text color="grey" class="mt-4 ml-2" @click="reset"  v-bind="attrs" v-on="on">
                                    <v-icon>mdi-rotate-right</v-icon>
                                </v-btn>
                            </template>
                            <span>reset</span>
                        </v-tooltip>
                </v-form>
            </v-card-text>
        </v-card>
        <v-snackbar
                    v-model="snackbar"
                    :timeout="timeout">
                    {{ text }}

                    <template v-slot:action="{ attrs }">
                        <v-btn
                        color="primary"
                        text
                        v-bind="attrs"
                        @click="snackbar = false">
                        Close
                        </v-btn>
                    </template>
        </v-snackbar>     
    </v-container>

  </div>
</template>

<script>
import Navbar from '../../../src/components/achats/Navbar'

export default {
    components:{Navbar},
    data(){
        return{

            photo64:"",
            description:"",
            dateEcheance: new Date().toISOString().substr(0, 10), 
            dateReception: new Date().toISOString().substr(0, 10),//date:2021-05-05
            prix:"",
            quantite:"",
            produit:null,
            fournisseur:null,
           
            fournisseurs:[],
            dataFournisseurs:null,
            fournisseursObj:[],
            fournisseurId:"",
            
            produits:[],
            dataProduits:null,
            produitsObj:[],

            produitsTable:[],
            produitsTableObj:{},

            dataCommande:"",

            reveal:false,
            menu: false,
            menu2:false,

            valid: true,
            dialog: false,

            snackbar:false,
            text: 'Fournisseur modifié',
            timeout: 2500,
         
//rules     
            quantiteRules:[
                v => /^\d+$/.test(v)||'saisir une quantité valide',
            ],
            prixRules:[
                v => /^\d+$/.test(v)||'saisir un prix valide',
            ], 
        }
    },
    methods:{
        getFournisseurs(){
             fetch("http://localhost:9001/api/fournisseurs")
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }

                    this.dataFournisseurs = data;
                    this.fournisseursObj = data

                    for(var i=0; i<this.dataFournisseurs.length; i++){                        
                        this.fournisseurs.push(this.fournisseursObj[i].societe)
                    }

                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });
        },
        getProduits(){
             fetch("http://localhost:9001/api/produits")
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }

                    this.dataProduits = data;
                    this.produitsObj = data

                    for(var i=0; i<this.dataProduits.length; i++){                        
                        this.produits.push(this.produitsObj[i].nom)
                    }

                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });
        },
        reset () {
            this.$refs.form.reset()
        },
        modifier(){

            for(var i=0; i<this.dataFournisseurs.length; i++){

                if(this.fournisseur == this.dataFournisseurs[i].societe)
                console.log("egale"+this.dataFournisseurs[i].id)
            }
        },
        ajouterProduit(){
            if(this.$refs.form.validate()){

                this.produitsTableObj['prix'] = this.prix
                this.produitsTableObj['quantite'] = this.quantite              
                this.produitsTableObj['nom'] = this.produit

                if(this.produitsTable.length == 0)
                {
                    this.produitsTable.push(this.produitsTableObj)
                }
                else{                  
                    for(var i=0; i<this.produitsTable.length; i++)
                    {
                        if(this.produitsTable[i].nom == this.produit)
                        {
                            this.produitsTable.splice(i,1)
                            break                        
                        }                               
                        
                    }
                    this.produitsTable.push(this.produitsTableObj)                   
                }              

                this.produitsTableObj = {}
                this.produit = null
                this.quantite = ""
                this.prix = ""
                this.$refs.form.resetValidation()

                this.reveal = true
            } 
            
        },
        ajouterQteCommande(idCommande){

            const requestOptions = {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
            };

            for(var i=0; i<this.dataProduits.length; i++)
            {
                for(var j=0; j<this.produitsTable.length; j++)
                {
                    if(this.dataProduits[i].nom == this.produitsTable[j].nom)
                    {
                        fetch("http://localhost:9001/api/qteCommandesFournisseur/"+this.dataProduits[i].id+"/"+idCommande+"/"+this.produitsTable[j].quantite, requestOptions)
                        .then(async response => {
                            const data = await response.json();

                            // check for error response
                            if (!response.ok) {
                                // get error message from body or default to response status
                                const error = (data && data.message) || response.status;
                                return Promise.reject(error);
                            }

                            this.reset()
                            this.snackbar = true
                            this.dataCommande = data
                        })
                        .catch(error => {
                        this.errorMessage = error;
                        console.error('There was an error!', error);
                        });
                    }
                }
 
            }
        },
        ajouterCommande: function(){
   
                for(var i=0; i<this.dataFournisseurs.length; i++){

                if(this.fournisseur == this.dataFournisseurs[i].societe)
                    this.fournisseurId = this.dataFournisseurs[i].id
                }

                const requestOptions = {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ dateEcheance: this.dateEcheance, dateReception:this.dateReception})             
                };

                 fetch("http://localhost:9001/api/commandesFournisseur/"+this.fournisseurId, requestOptions)
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response status
                        const error = (data && data.message) || response.status;
                        return Promise.reject(error);
                    }

                    this.snackbar = true
                    this.dataCommande = data                   
                    this.ajouterQteCommande(data.id)

                    this.produitsTable = []
                    this.reset()
                })
                .catch(error => {
                this.errorMessage = error;
                console.error('There was an error!', error);
                });
                
 
        },
    },
    created(){
        this.getFournisseurs()
        this.getProduits()
    },
    computed:{
        imgClick(){
            if(this.fournisseur){
                for(var i=0; i<this.dataFournisseurs.length; i++){   
                    if(this.fournisseursObj[i].societe == this.fournisseur)
                    this.photo64 = this.fournisseursObj[i].image64              
                }
            }
            else{
                this.photo64 = "/images/addImage.png"
            }
            return this.photo64
        }
    },
    mounted(){
        this.reset()
    }
}
</script>

<style>

</style>