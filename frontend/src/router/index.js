import { createRouter, createWebHistory } from "vue-router"
import HomeComponent from "@/components/HomeComponent.vue"
import LoginForm from "@/components/LoginForm.vue"
import RegistrationForm from "@/components/RegistrationForm.vue"
import RegistrationTable from "@/components/RegistrationTable.vue"

const routes = [
  {
    path: "/",
    name: "Home",
    component: HomeComponent
  },
  {
    path: "/login",
    name: "Login",
    component: LoginForm
  },
  {
    path: "/registration",
    name: "Registration",
    component: RegistrationForm
  },
  {
    path: "/reservation",
    name: "Reservation",
    component: RegistrationTable,
    meta: { requiresAuth: true } // Nastavení cesty, která vyžaduje autentizaci
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem("authToken")
  if (
    to.matched.some((record) => record.meta.requiresAuth) &&
    !isAuthenticated
  ) {
    next("/login")
  } else {
    next()
  }
})

export default router
