<template>
  <div>
    <h1>Rezervační Systém</h1>
    <LoginForm />
  </div>
</template>

<script>
import LoginForm from "@/components/LoginForm.vue"
import api from "@/services/api"

export default {
  components: {
    LoginForm
  },
  data() {
    return {
      uzivatele: []
    }
  },
  created() {
    this.fetchUzivatele()
  },
  methods: {
    fetchUzivatele() {
      api
        .getUzivatele()
        .then((response) => {
          this.uzivatele = response.data
        })
        .catch((error) => {
          console.error("Error fetching users:", error)
        })
    }
  }
}
</script>

<style scoped>
/* Styly pro úvodní stránku */
</style>
