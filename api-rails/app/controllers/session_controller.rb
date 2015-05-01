#Checks the authorization of the current user 
#Allows login of user if authorized
class SessionController < ApplicationController
  def create
    user = User.find_by(name: params[:session][:name])
    if user && user.authenticate(params[:session][:password])
      render json: user.session_api_key, status: 201
    else
      render json: {}, status: 401
    end
  end


def index
@keys = ApiKey.all
    render json: @keys 
  end

  def destroy
    api_key = ApiKey.find_by(params[:user_id])
    api_key.destroy
  	head :no_content
  end

end