<template>
  <div class="fournisseurs">
      <Navbar/>
        <v-container fluid >
            <v-row class="mb-1" >
                <v-col class="d-flex justify-end mr-5">
                    <AjoutFournisseur/>
                </v-col>           
            </v-row>
            <div v-if="data">
                <v-row dense>
                    <v-col cols="12" sm="6" md="4" lg="4"
                        v-for="fournisseur in fournisseurs"
                        :key="fournisseur.id">
                        <v-card >
                            <v-list-item three-line>
                                <v-list-item-avatar tile size="80" color="grey lighten-4">
                                    <img :src="fournisseur.image64" >
                                </v-list-item-avatar>
                                <v-list-item-content>
                                    <v-list-item-title class="headline mb-1">
                                        {{fournisseur.societe}}
                                    </v-list-item-title>
                                    <v-list-item-subtitle >{{fournisseur.email}}</v-list-item-subtitle>                                 
                                    <v-list-item-subtitle >{{fournisseur.website}}</v-list-item-subtitle>
                                    <v-list-item-subtitle >{{fournisseur.pays}}</v-list-item-subtitle>                           
                                </v-list-item-content>   
                            </v-list-item>

                            <v-card-actions class="mt-n4">
                                <v-spacer></v-spacer>
                                <v-tooltip bottom> 
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-btn icon v-bind="attrs" v-on="on">
                                            <v-icon>mdi-heart</v-icon>
                                        </v-btn>
                                    </template>
                                    <span>favorit</span>
                                </v-tooltip>

                            <DeleteFournisseur :fournisseur="fournisseur"/> 

                            <v-tooltip bottom > 
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-btn icon v-bind="attrs" v-on="on" router :to="{name: 'Fournisseur', params:{id: fournisseur.id}}">
                                            <v-icon>mdi-pencil</v-icon>
                                        </v-btn>
                                    </template>
                                    <span>modifier</span>
                                </v-tooltip>                   
                            </v-card-actions>
                        </v-card>
                    </v-col>
                </v-row>
            </div>
            <div v-else>aucun fournisseur n'existe</div>         
        </v-container>
  </div>
</template>

<script>
import AjoutFournisseur from '../../../src/components/achats/AjoutFournisseur'
import DeleteFournisseur from '../../../src/components/achats/DeleteFournisseur'
import Navbar from '../../../src/components/achats/Navbar'
export default {
    components:{AjoutFournisseur, Navbar, DeleteFournisseur},
    data(){
        return{
            fournisseurs:[],
            data:null,
            errorMessage:null,
        }
    },
    methods:{
        get(){
             fetch("http://localhost:9001/api/fournisseurs")
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }

                    this.data = data;
                    this.fournisseurs = data;

                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });
        }
    },
    created(){
            this.get()  
    },

}
</script>

<style>

</style>