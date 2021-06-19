<template>
  <div class="EditProduit">
      <Navbar/>
      <v-container fluid class="my-8 px-16">
           <v-card color="grey lighten-4 ">
            <v-card-title>
                Modifier article
            </v-card-title>
            <v-card-text class="pl-10 py4">
                <v-form class="px-3" ref="form" lazy-validation v-model="valid">
                    <v-row >
                        <v-col md="10" cols="10" sm="10" lg="10">
                            <v-img
                                lazy-src="/images/addImage.png"
                                height="100%"
                                max-width="250"
                                :src="imgClick"
                            ></v-img>
                        </v-col>
                        <v-col md="7" cols="10" sm="10" lg="7">
                            <v-text-field label="Nom" clearable v-model="titre" :counter="20" :rules="titreRules" required></v-text-field>
                        </v-col>
                        <v-col md="3" cols="10" sm="10" lg="3">
                            <v-file-input  v-model="photo" accept="image/png, image/jpeg, image/bmp, image/jpg" placeholder="photo" prepend-icon="mdi-camera" label="Photo"></v-file-input>
                            
                        </v-col>                 
                        <v-col md="10" cols="10" sm="10" lg="10">
                            <v-select
                                :items="types"
                                :menu-props="{ bottom: true, offsetY: true }"
                                label="Type Article"
                                v-model="type"
                                :rules="[v => !!v || 'type est obligatoire']"
                                required
                            ></v-select>
                        </v-col >
                        <v-col md="5" cols="10" sm="10" lg="5">
                            <v-text-field label="Prix" clearable v-model="prix" suffix="DH" :rules="prixRules" required></v-text-field>
                        </v-col>
                        <v-col md="5" cols="10" sm="10" lg="5">
                            <v-text-field label="Code Barre" clearable v-model="codeBarre" :rules="codeBarreRules" :counter="13"></v-text-field>
                        </v-col>
                        <v-col md="10" cols="10" sm="10" lg="10">
                            <v-textarea label="description" v-model="description" clearable  rows="1" row-height="50"></v-textarea>
                        </v-col>
                    </v-row>                
                    <v-btn text class="primary text mx-0 mt-3"  @click="modifier" :disabled="!valid">modifier</v-btn>
                
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
import Navbar from '../../../src/components/inventaire/Navbar'
export default {
    components: {Navbar},
    props: ['id'],
    data(){
        return{
            produit: {},

            titre: "",
            description:"",
            prix:"",
            codeBarre:"",
            type:"",
            types: ['Consommable', 'Service', 'Stockable'],
            photo:null,
            photo64:"",

//rules
           
            titreRules: [
                v => !!v || 'champ est obligatoire',
                v => (v && v.length <= 20) || 'Taille max est 10',
            ],
            codeBarreRules: [
                v => !!v || 'champ est obligatoire',
                v => (v && v.length == 13) || 'Saisir un code barre valide',
            ],
            prixRules: [
                v => !!v || 'champ est obligatoire',
            ],                        
//params

            valid: true,
            dialog: false,

            snackbar:false,
            text: 'Article Modifié',
            timeout: 2500,
            
        }
    },
    methods:{

        imageToBase64 () {
            var reader = new FileReader()
            reader.readAsDataURL(this.photo)
            reader.onload = () => { 
               this.photoBase64 = reader.result
            }
            reader.onerror = function (error) {
                console.log('Error uploading image: ', error)
            }
        },
        urltoFile(url, filename, mimeType){
            return (fetch(url)
                .then(function(res){return res.arrayBuffer();})
                .then(function(buf){return new File([buf], filename,{type:mimeType});})
            );
        },
        
       modifier: function(){

            if(this.$refs.form.validate())
            {     
                     if(this.photo)
                    {
                        this.imageToBase64 ()
                    }

                    //data fetching
                    const requestOptions = {
                        method: "PUT",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ nom: this.titre, description:this.description, prix: this.prix, codeBarre: this.codeBarre, typeArticle: this.type , image64: this.photo64})           
                    };

                    fetch("http://localhost:9001/api/produits/"+this.id, requestOptions)
                    .then(async response => {

                        const data = await response.json();

                        // check for error response
                        if (!response.ok) {
                            // get error message from body or default to response status
                            const error = (data && data.message) || response.status;
                            return Promise.reject(error);
                        }

                        this.snackbar = true
                

                })
                .catch(error => {
                this.errorMessage = error;
                console.error('There was an error!', error);
                });


    
            }

        },
        get(){
            
             fetch("http://localhost:9001/api/produits/"+this.id)
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }
                    
                    this.produit = data;

                    this.titre = this.produit.nom
                    this.description = this.produit.description
                    this.prix = this.produit.prix
                    this.codeBarre = this.produit.codeBarre
                    this.type = this.produit.typeArticle
                    this.photo64 = this.produit.image64

                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });
                
                
        },

    
    
    },
    computed:{
        imgClick(){
            if(this.photo){
                var reader = new FileReader()
                reader.readAsDataURL(this.photo)
                reader.onload = () => {
                this.photo64 = reader.result
                }
                reader.onerror = function (error) {
                console.log('Error uploading image: ', error)
                }           
            }
            return this.photo64
        }
    },
    created(){
            this.get()      
    },

}
        
</script>

<style>

</style>