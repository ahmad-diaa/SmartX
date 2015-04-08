Rails.application.routes.draw do
	get 'v/users/:user_id/rooms/:name'=>'rooms#find'
	get 'v/users/:user_id/rooms/:room_id/devices/:name'=>'devices#find'
    resources :types
    post 'session' => 'session#create'
      resources :users do
       resources :rooms do 
        resources :devices, param: :device_id 
      end
      end
    end

