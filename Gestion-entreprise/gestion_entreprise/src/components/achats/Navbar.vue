<template>

    <v-card class="overflow-hidden">
      <v-app-bar flat color="grey lighten-4" dense>
            <span>
            <v-app-bar-nav-icon v-if="!drawer" class="grey--text" @click="show"></v-app-bar-nav-icon>
            <v-app-bar-nav-icon v-else class="show hidden-md-and-down" @click="show"></v-app-bar-nav-icon>
            </span>
            <v-toolbar-title class="text-uppercase grey--text">
                <span class="font-weight-light">ikea - </span>
                <span>achat</span>
            </v-toolbar-title>
            <v-spacer></v-spacer>

            <v-menu offset-y transition="scale-transition">
                <template v-slot :activator="{ on, attrs }">
                    <v-btn v-bind="attrs" v-on="on" color="grey" text>
                        <v-icon left>expand_more</v-icon>
                        <span>Menu</span>
                    </v-btn>
                </template>
               
            </v-menu>
            <v-btn text color="grey">
                <router-link to="/">Sign out</router-link>
                <v-icon right>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
      
        
       <v-navigation-drawer v-model="drawer" class="primary" app  src="/images/image2.jpg"  >
             <v-list>
                <v-avatar size="80" class="grey darken-4 ml-16 my-6 " color="grey">
                    <img src="/images/image1.jpg">
                </v-avatar>
                <v-list-item v-for="link in links" :key="link.text" router :to="link.route">
                    <v-list-item-action>
                        <v-icon class="white--text" >{{link.icon}}</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title class="white--text">{{link.text}}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
    </v-card>
 
</template>

<script>
export default {
    data(){
        return{
            drawer: true,
            links: [
                {icon:'dashboard', text:'Achats', route:'/achats'},
                {icon:'mdi-account-multiple', text:'Commandes', route:'/commandes'},
                {icon:'dashboard', text:'Fournisseurs', route:'/fournisseurs'},
                {icon:'mdi-account-multiple', text:'Articles', route:'/produits'}              
            ],
        }
    },
    methods:{
           show(){
               this.drawer = !this.drawer;

           }

    }

}
</script>

<style>

</style>