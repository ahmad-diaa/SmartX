class SessionController < ApplicationController
  def create
    user = User.where("name = ?", params[:name]).first
    if user && user.authenticate(params[:password])
      render json: user.session_api_key, status: 201
    else
      render json: {}, status: 401
    end
  end
end