import axios from "axios"

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json"
  }
})

apiClient.interceptors.request.use((request) => {
  console.log("Starting Request", request)
  return request
})

apiClient.interceptors.response.use((response) => {
  console.log("Response:", response)
  return response
})

export default {
  getUzivatele() {
    return apiClient.get("/uzivatele")
  },
  getKurty() {
    return apiClient.get("/kurty")
  },
  getRezervace() {
    return apiClient.get("/reservation")
  },
  createRezervace(reservation) {
    return apiClient.post("/reservation", reservation)
  },
  deleteRezervace(id) {
    return apiClient.delete(`/reservation/${id}`)
  },
  login(user) {
    console.log("Login Request Data: ", user)
    return apiClient.post("/auth/login", user)
  },
  register(user) {
    return apiClient.post("/auth/register", user)
  }
}
