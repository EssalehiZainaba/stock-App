<template>
  <div>
    <Navbar/>
    <v-container fluid class="my-8 px-16">
        <v-row class="mb-1">
            <v-col class="d-flex justify-end mr-5">
                <v-btn large icon @click="edit=true" >
                    <v-icon>mdi-pencil</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        <v-card color="grey lighten-4 ">
            <v-card-title class="mb-4">
                Confirmation commande
                <div v-if="edit">         
                    <v-btn
                        :loading="loading3"
                        :disabled="loading3"
                        outlined
                        color="blue-grey"
                        class="ml-10 white--text"
                        @click="generatePDF"
                        >
                        <!--@click="loader = 'loading3'"-->
                        PDF
                        <v-icon
                            right
                            dark
                        >
                            mdi-cloud-download
                        </v-icon>
                    </v-btn>
                </div>
            </v-card-title>
           <v-card-text class="pl-10 py4">
                <v-form class="px-3 pr-10" ref="form" lazy-validation v-model="valid">
                    <v-row>
                        <v-col md="8" cols="8" sm="8" lg="8">
                            <v-text-field label="Fournisseur" outlined readonly v-model="fournisseur" ></v-text-field>                            
                        </v-col>   
                        <v-spacer></v-spacer>
                        <v-col md="3" cols="5" sm="3" lg="3">                              
                            <v-img
                                height="100%"
                                max-width="170"
                                :src="image64"                                   
                            ></v-img>                         
                        </v-col>                              
                        <v-col md="4" cols="12" sm="6" lg="4">
                            <v-text-field
                                v-model="dateEcheance"
                                label="Date d'échéance"                              
                                readonly
                                outlined>
                            </v-text-field>     
                        </v-col>
                        <v-col md="4" cols="12" sm="6" lg="4">
                            <v-text-field
                                v-model="dateReception"
                                label="Date de réception"
                                readonly
                                outlined>
                            </v-text-field>                                  
                        </v-col>
                        <v-col md="12" cols="12" sm="12" lg="12">                       
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
                                                Prix unitaire
                                            </th>
                                            <th class="text-left">
                                                Quantité commandée
                                            </th>
                                            <th class="text-left">
                                                Quantité reçu
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr
                                            v-for="produit in produitsTable"
                                            :key="produit.nom">
                                            <td>{{ produit.nom }}</td>
                                            <td>{{ produit.prix }}</td>
                                            <td>{{ produit.quantiteCommande }}</td>
                                            <td>
                                                <v-row>
                                                    <v-col md="3" cols="3" sm="4" lg="3">
                                                        <v-text-field :readonly="edit == 'true'" v-model="produit.quantiteRecu" ></v-text-field>
                                                    </v-col>
                                                </v-row>
                                            </td>                                                                                            
                                        </tr>
                                    </tbody>
                                </template>
                            </v-simple-table>                               
                        </v-col>
                    </v-row>
                    <v-btn  class=" text mx-0 mt-3" color="green" outlined @click="confirmer" :disabled="!edit">confirmer</v-btn>                           
                </v-form>
            </v-card-text>
        </v-card>
    </v-container>
  </div>
</template>

<script>
import Navbar from '../../../src/components/achats/Navbar'
import { jsPDF } from "jspdf"
import 'jspdf-autotable'

export default {
    components:{Navbar},
    props: ['id'],
    data(){
        return{
            commande: null,
            photo64:"",
            dateEcheance: new Date().toISOString().substr(0, 10),
            dateReception: new Date().toISOString().substr(0, 10),//date:2021-05-05
            prix:"",
            heading:"test",
            quantiteCommande:"",
            quantiteRecu:"",
            produit:null,
            fournisseur:null,
            image64: "",
            qteCommande:null,
            idProduit:"",
            prixTotal:"",
            dataFacture:null,

            edit:false,

            produitsTable:[],
            produitsTableObj:{},

            produitsTablePDF:[],
            produitsTableObjPDF:{},

            menu: false,
            menu2:false,
            valid: true,

            loading3: false,
            loader: null,

            quantiteRules:[
                v => /^\d+$/.test(v)||'saisir une quantité valide',
            ],
            prixRules:[
                v => /^\d+$/.test(v)||'saisir un prix valide',
            ], 
            items:[
                {title: "a", body:"aa"},
                {title: "b", body:"bb"},
            ]

        }
    },
    methods:{
        get(){          
            fetch("http://localhost:9001/api/commandesFournisseur/"+this.id)
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }
                    
                    this.commande = data;

                    this.dateEcheance = this.commande.dateEcheance
                    this.dateReception = this.commande.dateReception
                    this.fournisseur = this.commande.fournisseur['societe']
                    this.image64 = this.commande.fournisseur['image64']
                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });           
        },
        getQteCommande(){
            fetch("http://localhost:9001/api/qteCommandesFournisseur/"+this.id)
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }
                    
                    this.qteCommande = data;

                    for(var i=0; i<this.qteCommande.length; i++){  

                       this.produitsTableObj['prix'] = this.qteCommande[i].produit['prix']
                       this.produitsTableObj['idProduit'] = this.qteCommande[i].produit['id']
                       this.produitsTableObj['quantiteCommande'] = this.qteCommande[i].qtecommandeFournisseur
                       this.produitsTableObj['quantiteRecu'] = this.qteCommande[i].qtecommandeFournisseur                          
                       this.produitsTableObj['nom'] = this.qteCommande[i].produit['nom']
                       this.produitsTable.push(this.produitsTableObj)
                       this.produitsTableObj = {}
                    }

                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });   
        },
        confirmer(){
            if(this.$refs.form.validate())
            {
                let date = new Date().toISOString().slice(0, 10)
                for(var i=0; i<this.produitsTable.length; i++)
                {
                    this.prixTotal += this.produitsTable[i].prix*this.produitsTable[i].quantiteRecu
                }
                //data fetching
                const requestOptions = {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({prixTotal: this.prixTotal, dateFacture: date})             
                };

                fetch("http://localhost:9001/api/factures/"+this.id, requestOptions)
                    .then(async response => {
                    const data = await response.json();                                    
                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response status
                        const error = (data && data.message) || response.status;
                        return Promise.reject(error);
                    }

                    this.dataFacture = data

                    for(var i=0; i<this.produitsTable.length; i++){
                        console.log(this.produitsTable[i])
                        this.createQteFacture(this.produitsTable[i].idProduit, this.dataFacture.id, this.produitsTable[i].quantiteRecu, this.produitsTable[i].quantiteCommande)
                    }

                    this.edit = false
                })
                .catch(error => {
                this.errorMessage = error;
                console.error('There was an error!', error);
                });

            }
        },
        createQteFacture(idProduit, idFacture, qteRecu, qteCommande){
            const requestOptions = {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({qteRecu: qteRecu, qteManquante: qteCommande-qteRecu})             
                };

                fetch("http://localhost:9001/api/qteFacturesFournisseurs/"+idProduit+"/"+idFacture, requestOptions)
                    .then(async response => {
                    const data = await response.json();                                    
                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response status
                        const error = (data && data.message) || response.status;
                        return Promise.reject(error);
                    }
                })
                .catch(error => {
                this.errorMessage = error;
                console.error('There was an error!', error);
                });
        },
        generatePDF(){

            this.loading3 = true
            for(var i=0; i<this.produitsTable.length; i++)
            {
                this.prixTotal += this.produitsTable[i].prix*this.produitsTable[i].quantiteRecu
            }
            for(var i=0; i<this.produitsTable.length; i++){  

                this.produitsTableObjPDF['prix'] = this.produitsTable[i].prix
                this.produitsTableObjPDF['idProduit'] = this.produitsTable[i].id
                this.produitsTableObjPDF['quantiteCommande'] = this.produitsTable[i].quantiteCommande
                this.produitsTableObjPDF['quantiteRecu'] = this.produitsTable[i].quantiteRecu                        
                this.produitsTableObjPDF['nom'] = this.produitsTable[i].nom
                this.produitsTableObjPDF['prixTotal'] = this.prixTotal
                this.produitsTablePDF.push(this.produitsTableObjPDF)
                this.produitsTableObjPDF = {}
            }
            
            const columns = [
                {title: "Article", dataKey:"nom"},
                {title: "Prix unitaire", dataKey:"prix"},
                {title: "Quantité Commandée", dataKey:"quantiteCommande"},
                {title: "Quantité Reçu", dataKey:"quantiteRecu"},
                {title:"Prix Total", dataKey:"prixTotal" }               
            ];
            
            const doc = new jsPDF({
                orientation: "portrait",
                unit: "in",
                format: "letter"
            });

            doc.setFontSize(16).text("Facture N", 0.5, 1.0);
            doc.setLineWidth(0.01).line(0.5, 1.1, 8.0, 1.1);

            doc.autoTable({
                columns,
                body: this.produitsTablePDF,
                margin: {left: 0.5, top: 1.25}
            });

            doc.save(`${this.heading}.pdf`)

            this.loading3 = false
        },
    },
    created(){
        this.get()
        this.getQteCommande()
    },
    watch: {
      loader () {
        const l = this.loader
        this[l] = !this[l]

        setTimeout(() => (this[l] = false), 3000)

        this.loader = null
      },
    },
        
}
</script>

<style>

</style>