<template>
    <v-dialog max-width="900">
        <template v-slot:activator="{ on: dialog, attrs }">
            <v-tooltip bottom> 
                <template v-slot:activator="{ on: tooltip }">
                    <v-btn icon large v-bind="attrs" v-on="{ ...tooltip, ...dialog }" outlined text color="primary">
                        <v-icon>mdi-plus-thick</v-icon>
                    </v-btn>
                </template>
                <span>nouveau Article</span>
            </v-tooltip>
        </template>

        <v-card color="grey lighten-4 ">
            <v-card-title>
                    Ajout produit
            </v-card-title>
            <v-card-text class="pl-10 py4">
                    <v-form class="px-3" ref="form" lazy-validation v-model="valid">
                        <v-row >
                            <v-col md="7" cols="10" sm="10" lg="7">
                                <v-text-field label="Nom" clearable v-model="titre" :counter="20" :rules="titreRules" required></v-text-field>
                            </v-col>
                            <v-col md="3" cols="10" sm="10" lg="3">
                                <v-file-input :rules="PhotoRules" v-model="photo" accept="image/png, image/jpeg, image/bmp, image/jpg" placeholder="photo" prepend-icon="mdi-camera" label="Photo"></v-file-input>
    
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

export default {
    components: {},
    data(){
        return{
            titre:"",
            description:"",
            prix:"",
            codeBarre:"",
            type:"",
            types: ['Consommable', 'Service', 'Stockable'],
            produits: [],
            photo:null,

//rules
            PhotoRules: [ 
                v => !!v || 'champ est obligatoire',
                v =>(v && v.size < 2000000)  || 'Taille photo doit etre moin de 2 MB'
            ],
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
            text: 'Article Ajouté',
            timeout: 2500,

                    
        }
    },
    methods:{

        imageToBase64 () { 
            var reader = new FileReader()
            reader.readAsDataURL(this.photo)
            reader.onload = () => { 
               return this.photoBase64 = reader.result
            }
            reader.onerror = function (error) {
                console.log('Error uploading image: ', error)
            }
        },
        
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
                body: JSON.stringify({ nom: this.titre, description:this.description, prix: this.prix, codeBarre: this.codeBarre, typeArticle: this.type , image64: reader.result})
               
                };

                 fetch("http://localhost:9001/api/produits", requestOptions)
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
        

    }
}
</script>

<style>

</style>