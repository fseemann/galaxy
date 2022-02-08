<template>
  <div>
    <div>
      <form @submit.prevent="createGalaxy">
        <form-field v-slot="props" label="Name">
          <base-input v-model="formData.name" v-bind="props" name="name" />
        </form-field>
        <form-field v-slot="props" label="Planet count">
          <base-input v-model.number="formData.planetCount" v-bind="props" type="number" name="planetCount" />
        </form-field>
        <button>Create</button>
      </form>
    </div>
    <pre>{{ galaxies }}</pre>
  </div>
</template>

<script setup>
import useGalaxies from "../stores/use-galaxies";
import {computed, reactive} from "vue";
import FormField from "../components/form-field.vue";
import BaseInput from "../components/base-input.vue";

const store = useGalaxies();
store.fetchGalaxies()
const galaxies = computed(store.getGalaxies)

const formData = reactive({
  name: '',
  planetCount: 100
})

const createGalaxy = () => console.log('create galaxy')
</script>

<style scoped>

</style>