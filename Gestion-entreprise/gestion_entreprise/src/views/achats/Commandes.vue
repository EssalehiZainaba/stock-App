<template>
  <div class="commandes">
    <Navbar/>
    <v-container fluid >
        <v-row class="mb-1" >
            <v-col class="d-flex justify-end mr-5">
                <v-tooltip bottom> 
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn icon large v-bind="attrs" v-on="on" router :to="{name: 'Commande'}" outlined text color="primary">
                            <v-icon>mdi-plus-thick</v-icon>
                        </v-btn>
                    </template>
                    <span>nouvelle commande</span>
                </v-tooltip>
            </v-col>
        </v-row>
        <div>
            <v-card-title>
            <v-spacer></v-spacer>
            <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="Recherche"
                single-line
                hide-details
            ></v-text-field>
            </v-card-title>
            <v-data-table
                :headers="headers"
                :items="commandes"
                :search="search"
                :loading="loading"
                :page.sync="page"
                :items-per-page="itemsPerPage"
                hide-default-footer
                class="elevation-3"
                @page-count="pageCount = $event" >           
                <template v-slot:[`item.statut`]="{ item }">                  
                    <v-row dense>
                        <v-col>
                            <v-chip
                                :color="getColor(item.reception, item.echeance, item.statut)"
                                dark>
                                {{ item.statut }}
                            </v-chip>
                        </v-col>
                        <v-col>                          
                            <v-btn icon router :to="{name: 'Commande', params:{id: item.id}}" >
                                <v-icon>mdi-pencil</v-icon>
                            </v-btn>
                            <DeleteFacture/>
                            <v-tooltip bottom > 
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn v-bind="attrs" v-on="on" icon router :to="{name: 'Facture', params:{id: item.id}}">
                                    <v-icon> mdi-plus-thick</v-icon>
                                </v-btn>
                            </template>
                            <span>Confirmer</span>
                        </v-tooltip>
                        </v-col>
                    </v-row>               
                </template>

            </v-data-table>
                <div class="text-center pt-3">
                <v-pagination
                    v-model="page"
                    :length="pageCount"
                ></v-pagination>
            </div>
            
        </div>
    </v-container>
                       
  </div>
</template>

<script>
import Navbar from '../../../src/components/achats/Navbar'
import DeleteFacture from '../../components/achats/DeleteFacture'

export default {
    components:{Navbar, DeleteFacture},
    data(){
            return {
            dataCommande: null,
            errorMessage:null,
            dataFournisser:null,
            loading: true,

            page: 1,
            search: '',
            pageCount: 0,
            itemsPerPage: 9,
            headers: [
                {
                    text: 'Ref Commande',
                    align: 'start',
                    value: 'id',
                },         
                { text: 'Fournisseur', value: 'fournisseur' },
                { text: 'Date réception', value: 'reception' },
                { text: 'Date écheance', value: 'echeance' },
                { text: 'Statut', value: 'statut' },
            ],
            commandesObj: {},
            commandesObjj: {},
            commandes: [],
        }
    },
    methods:{
        getColor (reception, echeance) {
            let date = new Date().toISOString().slice(0, 10)

            if (date < reception && date > echeance){
                return 'orange'
            }         
            else if (date < echeance && date < reception ) return 'red'
            else return 'green'
        },
        getStatut(reception, echeance){
            let date = new Date().toISOString().slice(0, 10)
            if (date < reception && date > echeance){
                return "en retard"
            }         
            else if (date < echeance && date <  reception ) 
            {
                return "a expirée"
            }
            else{
                return "en cours"
            }
        },   
        get(){
            this.loading = true
            fetch("http://localhost:9001/api/commandesFournisseur")
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }

                    this.dataCommande = data;

                    this.loading = false

                    for(var i=0; i<this.dataCommande.length; i++)
                    {
                        this.commandesObj['reception'] = this.dataCommande[i].dateReception
                        this.commandesObj['echeance'] = this.dataCommande[i].dateEcheance
                        this.commandesObj['id'] = this.dataCommande[i].id
                        this.commandesObj['statut'] = this.getStatut(this.dataCommande[i].dateReception, this.dataCommande[i].dateEcheance)
                        this.commandesObj['fournisseur'] = this.dataCommande[i].fournisseur['societe']
                        this.commandes.push(this.commandesObj)                        
                        this.commandesObj = {}
                    }
                    
                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });
        },       
    },
    created(){
        this.get()      
    },
}
</script>

<style>

</style>