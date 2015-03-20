Rails.application.routes.draw do

  # namespace :api, :defaults => {:format => :json} do
    
  #   namespace :v1 do
    

  #     resources :users do
  #      resources :rooms, param: :room_id
  #     end
  #   end
  # end
  post 'session' => 'session#create'
  resources :users do
    resources :rooms, param: :room_id
      end

end