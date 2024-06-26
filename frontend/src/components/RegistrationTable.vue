<template>
  <b-container class="mt-5">
    <!-- Výběr hřišť -->
    <b-row>
      <b-col md="12" class="text-center">
        <b-button
          v-for="(field, index) in fields"
          :key="index"
          :variant="selectedField === index ? 'dark' : 'warning'"
          class="m-1"
          @click="selectField(index)"
        >
          {{ field }}
        </b-button>
      </b-col>
    </b-row>

    <!-- Tabulka rezervačních slotů -->
    <b-table
      v-if="selectedField !== null"
      :items="reservationSlots[selectedField]"
      :fields="dynamicTableFields"
      responsive="sm"
    >
      <!-- Sloupec s časem -->
      <template #cell(time)="data">
        {{ data.item.time }}
      </template>

      <!-- Sloupce pro jednotlivé dny -->
      <template
        v-for="(day, index) in dynamicDays"
        :key="index"
        v-slot:[`cell(${day.key})`]="{ item }"
      >
        <b-button
          :variant="getSlotVariant(item[day.key])"
          @click="toggleSlot(item, day.key)"
          block
        >
          {{ getButtonText(item[day.key]) }}
        </b-button>
      </template>
    </b-table>

    <!-- Potvrzovací tlačítko -->
    <b-button variant="warning" class="mt-3" @click="confirmReservation">
      Potvrdit
    </b-button>
  </b-container>
</template>

<script setup>
import { ref, onMounted, watch } from "vue"

// Pole pro názvy hřišť
const fields = [
  "Hřiště 1",
  "Hřiště 2",
  "Hřiště 3",
  "Hřiště 4",
  "Hřiště 5",
  "Hřiště 6"
]

// Dynamické pole pro názvy dnů
let dynamicDays = []

// Pole s rezervačními sloty
const reservationSlots = ref([])

// Index vybraného hřiště
let selectedField = ref(null)

// Funkce pro získání varianty tlačítka podle stavu slotu
const getSlotVariant = (slot) =>
  slot === "reserved" ? "danger" : slot === "selected" ? "warning" : "success"

// Funkce pro získání textu na tlačítku podle stavu slotu
const getButtonText = (slot) =>
  slot === "reserved"
    ? "Rezervováno"
    : slot === "selected"
    ? "Vybráno"
    : "Volno"

// Funkce pro přepínání stavu slotu
const toggleSlot = (slot, day) => {
  if (slot[day] === "reserved" || slot[day] === "selected") {
    slot[day] = false // Při stisknutí tlačítka "Rezervováno" nebo "Vybráno" přepínáme na "Volno"
  } else {
    slot[day] = "selected" // Při stisknutí tlačítka "Volno" přepínáme na "Vybráno"
  }
}

// Funkce pro potvrzení všech rezervací
const confirmReservation = () => {
  if (selectedField.value === null) {
    alert("Vyberte hřiště před potvrzením.")
    return
  }

  let slotSelected = false

  reservationSlots.value[selectedField.value].forEach((slot) => {
    for (let day in slot) {
      if (slot[day] === "selected") {
        slotSelected = true
        slot[day] = "reserved" // Změníme na "rezervované"
      }
    }
  })

  if (!slotSelected) {
    alert("Vyberte alespoň jeden termín před potvrzením.")
  }
}

// Funkce pro výběr konkrétního hřiště
const selectField = (index) => {
  selectedField.value = index
}

// Funkce pro generování dnů na základě aktuálního data
const generateDays = () => {
  dynamicDays = []
  const today = new Date()
  for (let i = 0; i < 7; i++) {
    const currentDate = new Date(today)
    currentDate.setDate(currentDate.getDate() + i)
    const formattedDate = `${currentDate.getDate()}.${
      currentDate.getMonth() + 1
    }`
    const dayKey = `day${i + 1}`
    dynamicDays.push({ key: dayKey, label: formattedDate })
  }
}

// Generování dnů při načtení komponenty a každý den
onMounted(generateDays)
watch(new Date().toLocaleDateString(), generateDays)

// Příprava rezervačních slotů s časy po hodině
const times = [
  "10:00",
  "11:00",
  "12:00",
  "13:00",
  "14:00",
  "15:00",
  "16:00",
  "17:00",
  "18:00",
  "19:00",
  "20:00"
]
fields.forEach(() => {
  const slots = times.map((time) => {
    const slot = { time }
    dynamicDays.forEach((day) => {
      slot[day.key] = false // Výchozí stav je "Volno"
    })
    return slot
  })
  reservationSlots.value.push(slots)
})

// Dynamické pole pro názvy sloupců tabulky
let dynamicTableFields = ref([])
const generateTableFields = () => {
  dynamicTableFields.value = [
    { key: "time", label: "Čas" },
    ...dynamicDays.map((day) => ({ key: day.key, label: day.label }))
  ]
}
onMounted(generateTableFields)
watch(dynamicDays, generateTableFields)
</script>

<style scoped>
.mt-5 {
  margin-top: 3rem;
}
</style>
