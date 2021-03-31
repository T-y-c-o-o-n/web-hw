<template>
    <div class="posts">
        <MainPagePost v-for="post in posts"
                      :post="post"
                      :key="post.id"/>
    </div>
</template>

<script>
import MainPagePost from "@/components/middle/MainPagePost";

export default {
    components: {
        MainPagePost
    },
    name: "Index",
    props: ["posts"]
}
</script>

<style scoped>

</style>