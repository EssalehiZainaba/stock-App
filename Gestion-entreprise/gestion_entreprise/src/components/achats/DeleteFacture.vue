<template>
  <v-dialog max-width="290" v-model="dialog" persistent transition="dialog-top-transition">
    <template v-slot:activator="{ on: dialog, attrs }">
                <v-btn icon v-bind="attrs"  v-on="{ ...tooltip, ...dialog }">
                    <v-icon>mdi-delete</v-icon>
                </v-btn>
    </template>
    <v-card>
        <v-card-title class="subtitle-1">
          Voulez-vous vraiment annuler cette commande ?
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="red lighten-1"
            text
            @click="supprimer(client.id)">
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

}
</script>

<style>

</style>