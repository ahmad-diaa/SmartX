class SessionController < ApplicationController
  def create
    user = User.find_by(name: params[:session][:name])
    if user && user.authenticate(params[:session][:password])
      render json: user.session_api_key, status: 201
    else
      render json: {}, status: 401
    end
  end

end