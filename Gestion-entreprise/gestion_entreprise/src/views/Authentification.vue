<template>
  <div class="authentification">     
    <v-container fluid  min-height="100px">
        <v-row class="d-flex justify-center">
            <v-col cols="10" sm="6" md="4" lg="3"  >             
                <v-card  flat color="#f5f5f5" class="text-center " min-height="400">
                    <v-avatar size="80" class="primary mt-7">
                        <img src="/images/logo.png" >
                    </v-avatar >
                    <v-form class="px-6 mt-8"  ref="form" lazy-validation>
                        <v-text-field label="login" v-model="username" prepend-icon="email"></v-text-field>
                        <v-text-field v-model="password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" 
                            :type="show1 ? 'text' : 'password'"
                            name="input-10-1"
                            label="password"
                            hint="8 caractères min"
                            counter
                            prepend-icon="lock"
                            @click:append="show1 = !show1">
                    </v-text-field>
                    <v-btn  class="indigo lighten-1 white--text text-center mt-8" @click="handleSubmit" rounded>SIGN IN</v-btn>
                    </v-form>
                </v-card>
            </v-col>
        </v-row>
      </v-container>
    <Footer/>
  </div>
</template>

<script>
import Footer from '../../src/components/public/Footer'
export default {
    components: {Footer},
    data(){
        return{
            username: '',
            password: '',
            show1: false,
//rules
            emailRules: [
                v => !!v || 'saisir un e-mail',
                v => /.+@.+\..+/.test(v) || 'saisir un e-mail valide',
            ],
        }
    },
    computed: {
        loggingIn () {
            return this.$store.state.authentication.status.loggingIn;
        }
    },
    created () {
        // reset login status
        this.$store.dispatch('authentication/logout');
    },
    methods: {
        handleSubmit (){
            if(this.$refs.form.validate()){
            this.submitted = true;
            const { username, password } = this;
            const { dispatch } = this.$store;
            if (username && password) {
                dispatch('authentication/login', { username, password });
            }
            }
        }
    }

}
</script>

<style scoped>
    .background{
        background-image: url("/images/image1.jpg");
        background-repeat: no-repeat;
        height: 100%; width: 100%; object-fit: contain
    }
    .authentification{
        background: url('/images/image1.jpg') no-repeat;
        background-size: cover;
        min-height: 100%;
    }
</style>
