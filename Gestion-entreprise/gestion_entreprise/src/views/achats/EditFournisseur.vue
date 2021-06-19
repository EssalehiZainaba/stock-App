<template>
  <div class="editFournisseur">
    <Navbar/>
    <v-container fluid class="my-8 px-16">
        <v-card color="grey lighten-4 ">
            <v-card-title>
                Modifier fournisseur
            </v-card-title>
            <v-card-text class="pl-10 py4">
                <v-form class="px-3" ref="form" lazy-validation v-model="valid">
                    <v-row >
                        <v-col md="7" cols="7" sm="7" lg="7">
                                <v-text-field label="Société" clearable v-model="societe" :counter="20" :rules="societeRules" required></v-text-field>                                                     
                            </v-col>
                            <v-spacer></v-spacer>
                            <v-col md="3" cols="5" sm="3" lg="3">                              
                                    <v-img
                                        height="100%"
                                        max-width="130"
                                        :src="imgClick"                                   
                                    ></v-img>                              
                            </v-col>                                                   
                            <v-col md="7" cols="7" sm="7" lg="7">
                                <v-text-field label="Email" clearable v-model="email" :rules="emailRules" required></v-text-field>                            </v-col>
                            <v-spacer></v-spacer> 
                            <v-col md="4" cols="5" sm="5" lg="4">
                                <v-file-input v-model="photo" accept="image/png, image/jpeg, image/bmp, image/jpg" placeholder="photo" prepend-icon="mdi-camera" label="Photo" required></v-file-input>   
                            </v-col>                          
                            <v-col md="6" cols="12" sm="6" lg="4" class="mt-5">
                                <v-autocomplete
                                    :items="countries"
                                    dense
                                    label="Pays"
                                    clearable
                                    v-model="pays"
                                    :rules="[v => !!v || 'champ est obligatoire']"
                                    required
                                ></v-autocomplete>                              
                            </v-col >
                            <v-col md="6" cols="12" sm="6" lg="4">
                                <v-text-field label="Ville" clearable v-model="ville"></v-text-field>
                            </v-col>
                            <v-col md="6" cols="12" sm="6" lg="4">
                                <v-text-field label="Adresse" clearable v-model="adresse"></v-text-field>
                            </v-col>
                            <v-col md="6" cols="12" sm="6" lg="6">
                                <v-text-field label="Téléphone" clearable v-model="phone" :rules="phoneRules" :counter="10"></v-text-field>
                            </v-col>
                            <v-col md="6" cols="12" sm="6" lg="6">
                                <v-text-field label="Compte Bancaire" clearable v-model="compteBancaire" :rules="compteRules" :counter="16"></v-text-field>
                            </v-col>
                            <v-col md="6" cols="12" sm="6" lg="6">
                                <v-text-field label="Site web" clearable v-model="website" prefix="www."></v-text-field>
                            </v-col>
                            <v-col md="6" cols="12" sm="6" lg="6">
                                <v-text-field label="Code postale" clearable v-model="codePostale" :rules="phoneRules" :counter="10"></v-text-field>
                            </v-col>                           
                            <v-col md="6" cols="12" sm="6" lg="12">
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
import Navbar from '../../../src/components/achats/Navbar'
import countries from "countries-list";
const countryCodes = Object.keys(countries.countries);

export default {
    components: {Navbar},
    props: ['id'],
    data(){
        return{
            fournisseurs: {},
            societe:"",
            pays:null,
            description:"",
            photo:null,
            ville:"",
            adresse:"",
            phone:"",
            email:"",
            photo64:"",
            compteBancaire:"",
            website:"",
            codePostale:"",
            countries:countryCodes.map(code => countries.countries[code].name),
//rules        

            societeRules: [
                v => !!v || 'champ est obligatoire',
                v => (v && v.length <= 20) || 'Taille max est 20',
            ],
            emailRules:[
                v => !!v || 'E-mail est obligatoire',
                v => /.+@.+\..+/.test(v) || 'E-mail doit etre valide',
            ],
            phoneRules:[
                v => /^\d+$/.test(v)||'saisir un numéro valide',
                v =>(v.length == 10)  || 'Taille max est 10'
            ],
            compteRules:[
                v => /^\d+$/.test(v)||'saisir un compte valide',
                v =>(v.length == 16)  || 'Taille max est 16'
            ],        
        //params

            valid: true,
            dialog: false,

            snackbar:false,
            text: 'Fournisseur modifié',
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
                    body: JSON.stringify({societe: this.societe, adresse:this.adresse, codePostale: this.codePostale, compteBancaire:this.compteBancaire, phone:this.phone, email:this.email, description:this.description, pays:this.pays, phone:this.phone, ville:this.ville, website:this.website, image64: this.photo64})              
                    };

                    fetch("http://localhost:9001/api/fournisseurs/"+this.id, requestOptions)
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
            
             fetch("http://localhost:9001/api/fournisseurs/"+this.id)
                .then(async response => {
                    const data = await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response statusText
                        const error = (data && data.message) || response.statusText;
                        return Promise.reject(error);
                    }
                    
                    this.fournisseur = data;

                    this.societe = this.fournisseur.societe
                    this.adresse = this.fournisseur.adresse
                    this.pays = this.fournisseur.pays
                    this.ville = this.fournisseur.ville
                    this.phone = this.fournisseur.phone
                    this.rue = this.fournisseur.rue
                    this.email = this.fournisseur.email
                    this.description = this.fournisseur.description
                    this.photo64 = this.fournisseur.image64
                    this.codePostale = this.fournisseur.codePostale
                    this.compteBancaire = this.fournisseur.compteBancaire
                    this.website = this.fournisseur.website

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
    }
}
</script>

<style>

</style>