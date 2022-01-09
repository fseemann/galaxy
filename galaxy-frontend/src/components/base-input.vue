<template>
  <input v-model="modelValue" @input="emitInput">
</template>

<script setup>

defineProps({
  modelValue: String
})

const emit = defineEmits(['update:modelValue'])

const emitInput = (event) => emit('update:modelValue', event.target.value)

</script>

<style scoped>

</style>
