Rails.application.routes.draw do

    post 'session' => 'session#create'
      resources :users do
       resources :rooms do 
        resources :devices, param: :device_id do
      get 'devices' => 'devices#get_all_type' 
  end
      end
      end
    end

