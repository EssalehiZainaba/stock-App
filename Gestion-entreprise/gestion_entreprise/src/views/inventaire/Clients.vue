<template>
    <div class="clients">
        <Navbar/>
        <v-container fluid >
            <v-row class="mb-1" >
                <v-col class="d-flex justify-end mr-5">
                    <AjoutClient/>
                </v-col>           
            </v-row>
            <div v-if="data">
            <v-row dense>
                <v-col cols="12" sm="6" md="4" lg="4"
                    v-for="client in clients"
                    :key="client.id">
                    <v-card >
                        <v-list-item three-line>
                            <v-list-item-avatar tile size="80" color="grey lighten-4">
                                <img :src="client.image64" >
                            </v-list-item-avatar>
                            <v-list-item-content>
                                <v-list-item-title class="headline mb-1">
                                    {{client.nom}} {{client.prenom}}
                                </v-list-item-title>
                                <v-list-item-subtitle >{{client.email}}</v-list-item-subtitle>                                 
                                <v-list-item-subtitle >{{client.phone}}</v-list-item-subtitle>
                                <v-list-item-subtitle >{{client.pays}}</v-list-item-subtitle>                               
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

                            <DeleteClient :client="client"/>

                            <v-tooltip bottom > 
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn icon v-bind="attrs" v-on="on" router :to="{name: 'Client', params:{id: client.id}}">
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
            <div v-else>aucun client n'existe</div>         
        </v-container>
    </div>
</template>

<script>
import AjoutClient from '../../../src/components/inventaire/AjoutClient'
import DeleteClient from '../../../src/components/inventaire/DeleteClient'
import Navbar from '../../../src/components/inventaire/Navbar'

export default {
    components:{AjoutClient, DeleteClient, Navbar},
    data(){
        return{
            clients:[],
            data:null,
            errorMessage:null,
        }
    },
    methods:{
        get(){
             fetch("http://localhost:9001/api/clients")
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }

                    this.data = data;
                    this.clients = data;

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