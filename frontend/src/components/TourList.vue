<template>
    <div>
        <v-list
        style="max-height: 800px"
        class="overflow-y-auto"
        >
            <template 
            v-for="(tour, i) in tour_data"
            >
                <v-list-item
                    :key="i"
                >
                    <v-list-item-content>
                            <v-card
                            outlined
                            elevation
                            color = "#fafafa"
                            class="tour_card mb-5 elevation-12"
                            >
                            <v-container
                            fluid
                            grid-list-lg
                            >
                                <v-layout
                                row
                                wrap
                                >
                                    <v-flex>
                                        <h1>
                                            {{tour.name}}
                                        </h1>
                                    </v-flex>
                                </v-layout>
                                
                                <v-layout
                                row
                                wrap
                                justify-center
                                >   
                                    
                                    <v-flex
                                    xs5
                                    >
                                        
                                        <v-card
                                        width = 100%
                                        height = 300px
                                        :img="frontend_url + '/images/' + tour.images[0].image"
                                        >
                                        </v-card>
                                    </v-flex>
                                    <v-flex
                                    xs7
                                    min-width = "200px"
                                    class = "text-center">
                                        <h4
                                        >
                                            {{tour.description}}
                                        </h4>
                                        <router-link
                                        :to="{path : '/tour/' + tour.id}"
                                        class = "router-link"
                                        >
                                            <v-btn
                                            large
                                            primary
                                            class = "mb-12"
                                            >
                                                More info
                                            </v-btn>
                                        </router-link>
                                    </v-flex>
                                </v-layout>
                            </v-container>
                        </v-card>
                    </v-list-item-content>
                </v-list-item>        
            </template>    
        </v-list>
    </div>
</template>

<script>

const FRONTEND_URL = process.env.VUE_APP_FRONTEND_URL

export default {
    name : "TourList",
    props : {
        tour_data: Array,
        routeId: String
    },
    data() {
        return {
            frontend_url: FRONTEND_URL,
        }
    }
}
</script>

<style>

h4 {
    padding-bottom : 15px;
}

.card {
    width : 75%;
}

.tour_cards {
    width : 100%;
}

.tour_img {
    object-fit : cover;
}

</style>
