
Rails.application.routes.draw do
	get 'v/users/:user_id/rooms/:name'=>'rooms#find'
  get 'v/users/:user_id/rooms/:room_id/devices/:name'=>'devices#find'

  get 'f/users/:user_id/rooms/:room_id/devices/:device_id'=>'devices#findFavorite'
    resources :types
    post 'session' => 'session#create'
    get 'users/:user_id/rooms/:id' =>'rooms#getName'
    get 'v/types/:name' =>'types#find'
      resources :users do
       resources :rooms do 
        resources :devices , param: :device_id do
        	resources :clickers
        end
      end
    end
    
         resources :users do
       resources :rooms do 
        resources :devices, param: :device_id do
                get 'devices' => 'devices#get_all_type' 
          resources :notes
        end
      end
      end
end
