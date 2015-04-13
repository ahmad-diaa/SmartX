Rails.application.routes.draw do
	get 'v/users/:user_id/rooms/:name'=>'rooms#find'
    resources :types
    post 'session' => 'session#create'
    get 'users/:user_id/rooms/:id' =>'rooms#getName'
      resources :users do
       resources :rooms do 
        resources :devices, param: :device_id 
      end
      end
    end

