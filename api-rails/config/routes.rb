Rails.application.routes.draw do
  namespace :api, :defaults => {:format => :json} do
    namespace :v1 do
      get     "/users",     to: "users#index"
      post    "/users",     to: "users#create"
      get     "/users/:id", to: "users#show"
      put     "/users/:id", to: "users#update"
      delete  "/users/:id", to: "users#destroy"

      #post  "/users/rooms", to:"rooms#create" 
      # get     "/users/:id/rooms",     to: "rooms#index"
      # #post    "/users/rooms/",     to: "rooms#create"
      # get     "/users/:id/rooms/:id", to: "rooms#show"
      # put     "/users/:id/rooms", to: "rooms#update"
      # delete  "/users/:id/rooms", to: "rooms#destroy"
      #resources :rooms

      resources :users do
       resources :rooms, param: :room_id do 
        resources :devices 
      end
      end
    end
  end
end