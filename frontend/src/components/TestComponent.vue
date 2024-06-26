<template>
  <b-container>
    <b-row class="mt-5">
      <b-col>
        <h1 class="text-center">Testovací Stránka</h1>
      </b-col>
    </b-row>
    <b-row class="mt-4">
      <b-col>
        <p>Toto je testovací stránka.</p>
      </b-col>
    </b-row>
    <b-row class="mt-4">
      <b-col>
        <b-button @click="handleClick">Klikni na mě</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "TestComponent",
  methods: {
    handleClick() {
      alert("Tlačítko bylo kliknuto!")
    }
  }
}
</script>

<style scoped>
.text-center {
  text-align: center;
}
</style>
