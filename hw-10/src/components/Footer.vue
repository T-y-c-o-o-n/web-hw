<template>
    <footer>
        <a href="/">Codeforces</a> users: {{ userCount }}, posts: {{ postCount }} &copy; 2020 by Mike Mirzayanov

    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["userCount", "postCount"]
}
</script>

<style scoped>

</style>