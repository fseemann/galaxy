<template>
  <div>
    <pre>{{ galaxies }}</pre>
  </div>
</template>

<script setup>
import useGalaxies from "../stores/use-galaxies";
import {computed} from "vue";

const store = useGalaxies();
store.fetchGalaxies()
const galaxies = computed(store.getGalaxies)
</script>

<style scoped>

</style>