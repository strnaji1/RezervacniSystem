<template>
  <div id="app">
    <b-navbar toggleable="lg" bg="warning">
      <b-navbar-brand href="/">My App</b-navbar-brand>
      <b-navbar-nav class="ml-auto">
        <b-nav-item to="/">Home</b-nav-item>
        <b-nav-item to="/test">Test</b-nav-item>
      </b-navbar-nav>
    </b-navbar>
    <b-container>
      <router-view />
    </b-container>
  </div>
</template>

<script>
export default {
  name: "App"
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #fffffe;
}
.navbar {
  background-color: #ffeeba; /* Světle žlutá barva */
}
</style>
