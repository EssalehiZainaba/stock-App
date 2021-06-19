import Vue from 'vue'
import VueRouter from 'vue-router'
import Authentification from '../views/Authentification.vue'
import Admin from '../views/inventaire/Admin.vue'
import Employes from '../views/inventaire/Employes.vue'
import Produits from '../views/inventaire/Produits.vue'
import Produit from '../views/inventaire/EditProduit.vue'
import Clients from '../views/inventaire/Clients.vue'
import Client from '../views/inventaire/EditClient.vue'
import Achats from '../views/achats/Achats.vue'
import Fournisseur from '../views/achats/EditFournisseur.vue'
import Fournisseurs from '../views/achats/Fournisseurs.vue'
import Commandes from '../views/achats/Commandes.vue'
import Commande from '../views/achats/AjoutCommande.vue'
import FactureFournisseur from '../views/achats/FactureFournisseur.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'authentification',
    component: Authentification
  },
  {
    path: '/admin',
    name: 'admin',
    component: Admin
  },
  {
    path: '/employes',
    name: 'Employes',
    component: Employes
  },
  {
    path: '/produits',
    name: 'produits',
    component: Produits,
    /*meta: {
      requiresAuth: true,
    }*/
  },
  {
    path: '/produit/:id',
    name: 'Produit',
    component: Produit,
    props: true
  },
  {
    path: '/clients',
    name: 'clients',
    component: Clients
  },
  {
    path: '/client/:id',
    name: 'Client',
    component: Client,
    props: true
  },
  {
    path: '/achats',
    name: 'Achats',
    component: Achats
  },
  {
    path: '/fournisseur/:id',
    name: 'Fournisseur',
    component: Fournisseur,
    props: true
  },
  {
    path: '/fournisseurs',
    name: 'Fournisseurs',
    component: Fournisseurs
  },
  {
    path: '/commandes',
    name: 'Commandes',
    component: Commandes
  },
  {
    path: '/commande',
    name: 'Commande',
    component: Commande
  },
  {
    path: '/facture/:id',
    name: 'Facture',
    component: FactureFournisseur,
    props: true
  },
  /*{
    path: '*',
    redirect: '/'
  }*/
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('user');

  if (authRequired && !loggedIn) {
    return next('/');
  }
  
    next();

})

/*router.beforeEach((to, from, next) => {

  if(to.matched.some(record => record.meta.requiresAuth))
  {
    if (!localStorage.getItem('user')) {
      next({name: 'authentification'})
    }
    else{
      next()
    }
  }else{
    next()
  }
})*/