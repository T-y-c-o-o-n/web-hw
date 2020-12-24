<template>
    <div class="middle">
        <Sidebar :posts="sidePosts"/>
        <main>
            <Index v-if="page === 'Index'" :users="users"
                   :postsAndCommentCounts="postsAndCommentCounts"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <Post v-if="page === 'Post'" :post="post" :comments="postComments" :users="users" :key="post.id"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Enter from "@/components/middle/Enter";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";
import Register from "@/components/middle/Register";
import Users from "@/components/middle/Users";
import Post from "@/components/middle/Post";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: null
        }
    },
    components: {
        WritePost,
        Enter,
        Register,
        Users,
        Index,
        Post,
        Sidebar,
        EditPost
    },
    props: ["postsAndComments", "users"],
    computed: {
        sidePosts: function () {
            return this.postsAndComments
                .map(postAndComs => postAndComs.post)
                .sort((a, b) => b.id - a.id).slice(0, 2);
        },
        postsAndCommentCounts: function () {
            return this.postsAndComments.map((postAndComs) => ({
                post: postAndComs.post,
                commentCount: postAndComs.comments.length
            }))
        },
        postComments: function () {
            return this.postsAndComments
                .filter((postAndComments) => postAndComments.post.id === this.post.id)[0].comments;
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page);
        this.$root.$on("onShowPost", (post) => {
            this.post = post;
            this.$root.$emit("onChangePage", "Post");
        });
    }
}
</script>

<style scoped>

</style>