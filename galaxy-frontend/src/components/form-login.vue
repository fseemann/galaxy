<template>
<div>
  <h2>Login</h2>
  <form>
    <form-field label="Username" description="Enter your email." v-slot="props">
      <input v-bind="props" id="username" name="username" type="text" />
    </form-field>
    <form-field label="Password" v-slot="props">
      <input v-bind="props" id="password" name="password" type="password" />
    </form-field>
    <button>Submit</button>
  </form>
</div>
</template>

<script setup>
import FormField from './form-field.vue'</script>
