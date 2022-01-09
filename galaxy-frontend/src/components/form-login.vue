<template>
<div>
  <h2>Login</h2>
  <form method="post" action="/api/users/login" @submit.prevent>
    <form-field label="Username" description="Enter your email." v-slot="props">
      <base-input v-bind="props" name="username" type="text" />
    </form-field>
    <form-field label="Password" v-slot="props">
      <base-input v-bind="props" name="password" type="password" />
    </form-field>
    <button>Submit</button>
  </form>
</div>
</template>

<script setup>
import FormField from './form-field.vue'
import BaseInput from './base-input.vue'</script>
