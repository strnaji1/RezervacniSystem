module.exports = {
  chainWebpack: (config) => {
    config.resolve.alias.set("vue$", "vue/dist/vue.esm-bundler.js")
  },
  configureWebpack: {
    resolve: {
      alias: {
        vue$: "vue/dist/vue.esm-bundler.js"
      }
    }
  },
  devServer: {
    port: 3000, // Přidáno: nastavení portu na 3000
    hot: true, // Aktivuje HMR
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true
      }
    }
  },
  outputDir: "dist"
}
