<template>
  <div class="EditClient">
      <Navbar/>
      <v-container fluid class="my-8 px-16">
           <v-card color="grey lighten-4 ">
            <v-card-title>
                Modifier client
            </v-card-title>
            <v-card-text class="pl-10 py4">
                <v-form class="px-3" ref="form" lazy-validation v-model="valid">
                    <v-row >
                        <v-col md="7" cols="7" sm="7" lg="7">
                                <v-text-field label="Nom" clearable v-model="nom" :counter="15" :rules="nomRules" required></v-text-field>                                                     
                            </v-col>
                            <v-spacer></v-spacer>
                            <v-col md="3" cols="5" sm="3" lg="3">                              
                                    <v-img
                                        height="100%"
                                        max-width="150"
                                        :src="imgClick"                                   
                                    ></v-img>                              
                            </v-col>                                                   
                             <v-col md="7" cols="7" sm="7" lg="7">
                                <v-text-field label="Prénom" clearable v-model="prenom" :counter="15" :rules="prenomRules" required></v-text-field>
                            </v-col>
                            <v-spacer></v-spacer> 
                            <v-col md="4" cols="5" sm="4" lg="4">
                                <v-file-input v-model="photo" accept="image/png, image/jpeg, image/bmp, image/jpg" placeholder="photo" prepend-icon="mdi-camera" label="Photo" ></v-file-input>   
                            </v-col>                          
                            <v-col md="6" cols="12" sm="6" lg="4" class="mt-5">
                                <v-autocomplete
                                    :items="countries"
                                    dense
                                    label="Pays"
                                    clearable
                                    v-model="pays"
                                    :rules="[v => !!v || 'type est obligatoire']"
                                    required
                                ></v-autocomplete>                              
                            </v-col >
                            <v-col md="6" cols="12" sm="6" lg="4">
                                <v-text-field label="Ville" clearable v-model="ville"></v-text-field>
                            </v-col>
                            <v-col md="6" cols="12" sm="6" lg="4">
                                <v-text-field label="Rue" clearable v-model="rue"></v-text-field>
                            </v-col>
                            <v-col md="6" cols="12" sm="6" lg="6">
                                <v-text-field label="Email" clearable v-model="email" :rules="emailRules" required></v-text-field>
                            </v-col>
                            <v-col md="12" cols="12" sm="12" lg="6">
                                <v-text-field label="Téléphone" clearable v-model="phone" :rules="phoneRules" :counter="10"></v-text-field>
                            </v-col>
                            <v-col md="12" cols="12" sm="12" lg="12">
                                <v-textarea label="description" v-model="description" clearable rows="1" row-height="50"></v-textarea>
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

import countries from "countries-list";
const countryCodes = Object.keys(countries.countries);

export default {
    components: {Navbar},
    props: ['id'],
    data(){
        return{
                    client: {},
                    nom:"",
                    prenom:"",
                    description:"",
                    photo:null,
                    pays:null,
                    ville:"",
                    rue:"",
                    phone:"",
                    email:"",
                    photo64:"",
                    countries:countryCodes.map(code => countries.countries[code].name),
        //rules        
                    
                    titreRules: [
                        v => !!v || 'champ est obligatoire',
                        v => (v && v.length <= 15) || 'Taille max est 10',
                    ],
                    nomRules:[
                        v => !!v || 'champ est obligatoire',
                        v => (v && v.length <= 15) || 'Taille max est 10',
                    ],
                    prenomRules:[
                        v => !!v || 'champ est obligatoire',
                        v => (v && v.length <= 15) || 'Taille max est 10',
                    ],
                    emailRules:[
                        v => !!v || 'E-mail est obligatoire',
                        v => /.+@.+\..+/.test(v) || 'E-mail doit etre valide',
                    ],
                    phoneRules:[
                        v => /^\d+$/.test(v)||'saisir un numéro valide',
                        v =>(v.length == 10)  || 'Taille max est 10'
                    ],
                
        //params

                    valid: true,
                    dialog: false,

                    snackbar:false,
                    text: 'Client modifié',
                    timeout: 2500,
        }
    },
    methods:{
        imageToBase64() { 
            var reader = new FileReader()
            reader.readAsDataURL(this.photo)
            reader.onload = () => { 
               this.photoBase64 = reader.result
            }
            reader.onerror = function (error) {
                console.log('Error uploading image: ', error)
            }
            
        },       
        modifier: function(){

            if(this.$refs.form.validate())
            {     

                    if(this.photo)
                    {
                        this.imageToBase64()
                    }
                    
                    //data fetching
                    const requestOptions = {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ nom: this.nom, prenom:this.prenom, pays: this.pays, ville:this.ville, phone:this.phone, rue:this.rue, email:this.email, description:this.description, image64: this.photo64})              
                    };

                    fetch("http://localhost:9001/api/clients/"+this.id, requestOptions)
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
            
             fetch("http://localhost:9001/api/clients/"+this.id)
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }
                    
                    this.client = data;

                    this.nom = this.client.nom
                    this.prenom = this.client.prenom
                    this.pays = this.client.pays
                    this.ville = this.client.ville
                    this.phone = this.client.phone
                    this.rue = this.client.rue
                    this.email = this.client.email
                    this.description = this.client.description
                    this.photo64 = this.client.image64

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