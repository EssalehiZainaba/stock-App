<template>

  <div class="produits">
       <Navbar/>
       <div v-if="data">
            <v-container fluid >
                <v-row class="mb-2" >
                    <v-col class="d-flex justify-end mr-5">
                        <AjoutProduit/>
                    </v-col>           
                </v-row>

                <v-row dense>
                    <v-col cols="6" sm="3" md="2" lg="2"
                        v-for="produit in produits" :key="produit.id">
                        <v-hover v-slot="{ hover }">
                            <v-card  >
                                <v-img
                                    :src="produit.image64"
                                    class="white--text align-end"
                                    gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                    height="120px">
                                    <v-expand-transition>
                                        <div
                                        v-if="hover"
                                        class="d-flex transition-fast-in-fast-out primary darken-1 v-card--reveal white--text body-2"
                                        style="height: 100%;">
                                        en stock : 12,00 <br> prévision: 12,00
                                        </div>
                                    </v-expand-transition>
                                    <v-card-title v-text="produit.nom"></v-card-title>
                                </v-img>

                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    
                                    <v-tooltip bottom> 
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn icon v-bind="attrs" v-on="on" @click="loading = true">
                                                <v-icon>mdi-heart</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>favorit</span>
                                    </v-tooltip>

                                    <DeleteProduit :produit="produit"/>     

                                    <v-tooltip bottom > 
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn icon v-bind="attrs" v-on="on" router :to="{name: 'Produit', params:{id: produit.id}}">
                                                <v-icon>mdi-pencil</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>modifier</span>
                                    </v-tooltip>
                                    
                                </v-card-actions>
                            </v-card>
                        </v-hover>
                    </v-col>    
                </v-row>
            </v-container>
        </div>
    <div v-else>
        <v-progress-linear
            indeterminate
            height="5"
            color="primary darken-1">
        </v-progress-linear>
           <!-- <v-row dense>
            <v-col cols="6" sm="3" md="2" lg="2"
                v-for="produit in produits" :key="produit.id">
                 <v-sheet
                    :color="`grey ${theme.isDark ? 'darken-2' : 'lighten-4'}`"
                    >
                    <v-skeleton-loader
                    class="mx-auto"               
                    type="card"                  
                    ></v-skeleton-loader>
                </v-sheet>
            </v-col>
            </v-row>-->
    </div>
  </div>
  
  
</template>

<script>
import AjoutProduit from '../../../src/components/inventaire/AjoutProduit'
import DeleteProduit from '../../../src/components/inventaire/DeleteProduit'
import Navbar from '../../../src/components/inventaire/Navbar'

export default {
    components: { AjoutProduit, DeleteProduit, Navbar},
    data(){
        return{
            produits: [],
            data:null,
            errorMessage:null,
            link: {route:'/produit'},
        }
      
    },
    watch: {
        loading (val) {
            if (!val) 
            return setTimeout(() => (this.loading = false), 3000)
        },
    },
    inject: {
      theme: {
        default: { isDark: false },
      },
    },
    methods:{
        get(){
             fetch("http://localhost:9001/api/produits")
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }

                    this.data = data;
                    this.produits = data;              

                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });
        },
    },
    created(){
        //this.loading = true
        this.get()      
    },
    
     
}

</script>

<style>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: .8;
  position: absolute;
  width: 100%;
}
</style>