Rails.application.routes.draw do

    post 'session' => 'session#create'
      resources :users do
       resources :rooms do 
        resources :devices, param: :device_id do
        	resources :notes
        end
      end
      end
    end

