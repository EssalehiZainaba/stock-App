<template>
    <div class="employes">
        <Navbar/>
        <v-container fluid class="my-8">
            <v-row>
                <v-col cols="12" sm="6" md="4" lg="3" v-for="person in team" :key="person.name" >
                    <v-card flat color="#f5f5f5" class="text-center">
                        <v-avatar size="80" class="grey darken-4" color="grey">
                            <img :src="person.avatar">
                        </v-avatar>
                        <v-card-text>
                            <div class="subheading">{{person.name}}</div>
                            <div class="grey--text">{{person.role}}</div>
                        </v-card-text>
                        <v-card-actions flat color="grey">
                            <v-icon small left class="ml-11" color="grey">message</v-icon>
                            <span class="message">Message</span>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>
import Navbar from '../../../src/components/inventaire/Navbar'

export default {
    components: {Navbar},
    data(){
        return{
            team:[
                {name:'Ali', role: 'software engineer', avatar : '/images/image1.jpg'},
                {name:'Ahmed', role: 'Graphic Designer', avatar : '/images/image2.jpg'},
                {name:'Mohamed', role: 'Web Developper', avatar : '/images/logo.png'}
            ],
            produits:[]
        }
    },
    mounted(){
        fetch("http://localhost:9001/api/produits").then((res) => res.json()).then((data) => this.produits = data)
          .catch(err => console.log(err.message));
    },
    
    
}
</script>

<style>

</style>