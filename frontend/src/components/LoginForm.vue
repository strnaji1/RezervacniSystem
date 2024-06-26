<template>
  <b-container class="mt-5">
    <b-row>
      <b-col md="6" class="mx-auto">
        <b-card title="Přihlášení">
          <b-form @submit.prevent="login">
            <b-form-group label="Email" label-for="email">
              <b-form-input
                id="email"
                v-model="email"
                type="email"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Heslo" label-for="password">
              <b-form-input
                id="password"
                v-model="password"
                type="password"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="warning">Přihlásit</b-button>
            <router-link to="/registration" class="btn btn-link"
              >Registrace</router-link
            >
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
import api from "@/services/api"

const email = ref("")
const password = ref("")
const router = useRouter()

const login = async () => {
  if (email.value && password.value) {
    try {
      const requestBody = {
        email: email.value,
        heslo: password.value
      }
      const response = await api.login(requestBody)
      if (response.data.message === "Login successful") {
        router.push(response.data.redirectUrl)
      } else {
        alert("Login failed. Please check your credentials.")
      }
    } catch (error) {
      alert("An error occurred during login. Please try again.")
    }
  } else {
    alert("Vyplňte všechny údaje.")
  }
}
</script>

<style scoped>
.btn-link {
  margin-top: 10px;
}
</style>
