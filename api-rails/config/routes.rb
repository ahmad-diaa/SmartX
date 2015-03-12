Rails.application.routes.draw do
  resources :api_keys, except: [:new, :edit]
  namespace :api, :defaults => {:format => :json} do
    namespace :v1 do
      get     "/users",     to: "users#index"
      post    "/users",     to: "users#create"
      get     "/users/:id", to: "users#show"
      put     "/users/:id", to: "users#update"
      delete  "/users/:id", to: "users#destroy"
      resources :users
    end
  end
end