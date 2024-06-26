<template>
  <div>
    <h1>Rezervace</h1>
    <table class="table">
      <thead>
        <tr>
          <th>Uzivatel</th>
          <th>Kurt</th>
          <th>Čas</th>
          <th>Akce</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="rezervace in rezervaceList" :key="rezervace.id">
          <td>{{ rezervace.uzivatelId }}</td>
          <td>{{ rezervace.kurtId }}</td>
          <td>{{ rezervace.cas }}</td>
          <td>
            <button
              class="btn btn-danger"
              @click="deleteRezervace(rezervace.id)"
            >
              Smazat
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import api from "@/services/api"

export default {
  data() {
    return {
      rezervaceList: []
    }
  },
  created() {
    this.fetchRezervace()
  },
  methods: {
    fetchRezervace() {
      api.getRezervace().then((response) => {
        this.rezervaceList = response.data
      })
    },
    deleteRezervace(id) {
      api.deleteRezervace(id).then(() => {
        this.fetchRezervace()
      })
    }
  }
}
</script>
