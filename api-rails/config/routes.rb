Rails.application.routes.draw do
    post 'session' => 'session#create'

      get "/types/:name",  to: "types#index"
      resources :types
      resources :users do
       resources :rooms, param: :room_id do 
        resources :devices 
      end
      end
    end
#   end
# end
