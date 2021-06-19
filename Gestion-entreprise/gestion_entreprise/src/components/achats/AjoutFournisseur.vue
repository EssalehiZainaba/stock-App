<template>
    <v-dialog max-width="900">
        <template v-slot:activator="{ on: dialog, attrs }">
                <v-tooltip bottom> 
                        <template v-slot:activator="{ on: tooltip }">
                            <v-btn icon large v-bind="attrs" v-on="{ ...tooltip, ...dialog }" outlined text color="primary">
                                <v-icon>mdi-plus-thick</v-icon>
                            </v-btn>
                        </template>
                        <span>nouveau Fournisseur</span>
                </v-tooltip>
        </template>

            <v-card color="grey lighten-4 ">
                <v-card-title>
                    Ajout fournisseur
                </v-card-title>
                <v-card-text class="pl-10 py4">
                    <v-form class="px-3" ref="form" lazy-validation v-model="valid">
                        <v-row>
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
                                <v-file-input :rules="PhotoRules" v-model="photo" accept="image/png, image/jpeg, image/bmp, image/jpg" placeholder="photo" prepend-icon="mdi-camera" label="Photo" required></v-file-input>   
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
                        <v-btn text class="primary text mx-0 mt-3" @click="ajouter" :disabled="!valid">Ajouter</v-btn>
                        
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
    </v-dialog>
</template>

<script>
import countries from "countries-list";
const countryCodes = Object.keys(countries.countries);

export default {
        data(){
        return{
            fournisseurs: [],
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
            PhotoRules:[
                v => !!v || 'champ est obligatoire',
                v =>(v && v.size < 2000000)  || 'Taille photo doit etre moin de 2 MB'
            ],
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
            text: 'Client Ajouté',
            timeout: 2500,
        }
    },
    methods:{
        ajouter: function(){
            if(this.$refs.form.validate())
            {
                //convet photo to base64
                var reader = new FileReader()
                reader.readAsDataURL(this.photo)
                reader.onload = () => { 
                this.photoBase64 = reader.result


                //data fetching
                const requestOptions = {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ societe: this.societe, adresse:this.adresse, codePostale: this.codePostale, compteBancaire:this.compteBancaire, phone:this.phone, email:this.email, description:this.description, image64: reader.result, pays:this.pays, phone:this.phone, ville:this.ville, website:this.website})             
                };

                 fetch("http://localhost:9001/api/fournisseurs", requestOptions)
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
                    location.reload();
                })
                .catch(error => {
                this.errorMessage = error;
                console.error('There was an error!', error);
                });
                
                }
                reader.onerror = function (error) {
                    console.log('Error uploading image: ', error)
                }
    
            }
        },
        reset () {
        this.$refs.form.reset()
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
            else{
                this.photo64 = "/images/addImage.png"
            }
            return this.photo64
        }
    }

}
</script>

<style>

</style>