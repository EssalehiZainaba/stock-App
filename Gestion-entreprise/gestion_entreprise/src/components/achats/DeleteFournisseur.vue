<template>
   <v-dialog max-width="290" v-model="dialog" persistent transition="dialog-top-transition">
    <template v-slot:activator="{ on: dialog, attrs }">
        <v-tooltip bottom> 
            <template v-slot:activator="{ on: tooltip }">
                <v-btn icon v-bind="attrs"  v-on="{ ...tooltip, ...dialog }">
                    <v-icon>mdi-delete</v-icon>
                </v-btn>
            </template>
            <span>supprimer</span>
        </v-tooltip>
    </template>
    <v-card>
        <v-card-title class="subtitle-1">
          Voulez-vous vraiment supprimer ce client?
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="red lighten-1"
            text
            @click="supprimer(fournisseur.id)">
            oui
          </v-btn>
          <v-btn
            color="primary"
            text
            @click="dialog = false">
            non
          </v-btn>
        </v-card-actions>
      </v-card>
  </v-dialog>
</template>

<script>
export default {

    props: ['fournisseur'],
    data(){
        return{
            dialog: false,
        }
    },
    methods: {

        supprimer(id){

            const requestOptions = {
                method: "DELETE",
            };

            fetch('http://localhost:9001/api/fournisseurs/'+id, requestOptions)
                .then(async response => {
                    const data = await response;
                    // check for error response
                    if (!response.ok) {
                        const error = (data && data.message) || response.status;
                        return Promise.reject(error);
                    }
                    this.dialog = false;
                    location.reload();
                })
                .catch(error => {
                    this.errorMessage = error;
                    console.error("There was an error!", error);
                });
        }

    }
}
</script>

<style>

</style>