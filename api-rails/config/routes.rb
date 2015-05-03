
Rails.application.routes.draw do
  get 'v/users/:name'=>'users#security'
  get 'v/users/:user_id/rooms/:name'=>'rooms#find'
  get 'v/users/:id/:securityA'=>'users#answer'
	get 'v/users/:user_id/rooms/:name'=>'rooms#find'
  get 'v/users/:user_id/rooms/:room_id/devices/:name'=>'devices#find'
  delete 'v/users/:user_id/rooms/:room_id/devices/:name'=>'devices#destroy'
  get 'f/users/:user_id/rooms/:room_id/devices/:device_id'=>'devices#findFavorite'
  resources :types
  get 'session' => 'session#index'
  post 'session' => 'session#create'
  delete 'session/:user_id' => 'session#destroy'
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

  resources :users do
    resources :rooms do 
      resources :plugs
    end
  end

end
