<template>
<div>
  <h2 class="text-2xl">Login</h2>
  <form method="post" @submit.prevent="login">
    <form-field label="Username" description="Enter your email." v-slot="props">
      <base-input v-model="formData.username" v-bind="props" name="username" type="text" />
    </form-field>
    <form-field label="Password" v-slot="props">
      <base-input v-model="formData.password" v-bind="props" name="password" type="password" />
    </form-field>
    <button>Submit</button>
  </form>
  <button type="button" @click="users.logout">Logout</button>
</div>
</template>

<script setup>
import FormField from './form-field.vue'
import BaseInput from './base-input.vue'
import useUsers from "../stores/users";
import {reactive} from "vue";

const formData = reactive({
  username: '',
  password: ''
})

const users = useUsers()
const login = () => {
  users.login(formData)
}
</script>
