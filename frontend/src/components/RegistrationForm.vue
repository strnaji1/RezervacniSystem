<template>
  <b-container class="mt-5">
    <b-row>
      <b-col md="6" class="mx-auto">
        <b-card title="Registrace">
          <b-form @submit.prevent="register">
            <b-form-group label="Jméno" label-for="name">
              <b-form-input
                id="name"
                v-model="name"
                type="text"
                required
              ></b-form-input>
            </b-form-group>
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
            <b-form-group label="Potvrzení hesla" label-for="confirmPassword">
              <b-form-input
                id="confirmPassword"
                v-model="confirmPassword"
                type="password"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary">Registrovat</b-button>
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

const name = ref("")
const email = ref("")
const password = ref("")
const confirmPassword = ref("")
const router = useRouter()

const register = () => {
  if (password.value !== confirmPassword.value) {
    alert("Passwords do not match.")
    return
  }

  api
    .register({
      jmeno: name.value,
      email: email.value,
      heslo: password.value,
      confirmHeslo: confirmPassword.value
    })
    .then((response) => {
      if (response.data === "Registration successful") {
        router.push("/login")
      } else {
        alert("Registration failed: " + response.data)
      }
    })
    .catch((error) => {
      console.error("Registration error:", error)
      alert("An error occurred during registration. Please try again.")
    })
}
</script>

<style scoped>
.mt-5 {
  margin-top: 3rem;
}
</style>
