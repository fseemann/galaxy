<template>
<div class="flex flex-col">
  <label class="font-bold" :for="inputId">{{label}}</label>
  <slot :id="inputId" v-bind="{ 'aria-describedby': descriptionId}"/>
  <span class="text-sm" :id="descriptionId">{{description}}</span>
</div>
</template>

<script setup>
import { computed } from 'vue'

defineProps({
  label: String,
  description: String
})

const inputId = computed(() => Math.random().toString(36).substring(1, 6))
const descriptionId = computed(() => Math.random().toString(36).substring(1, 6))
</script>

<style scoped>

</style>
