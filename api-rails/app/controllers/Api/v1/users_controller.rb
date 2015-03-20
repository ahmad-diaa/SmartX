class API::V1::UsersController < ApplicationController
  before_action :set_user, only: [:show, :update, :destroy]
  # Returns list of users. This requires authorization
  def index
    @users =  User.all
    render json: @users if stale?(etag: @users.all, last_modified: @users.maximum(:updated_at))
  end

  # def show
  #   render json: User.find(params[:id])
  # end
  
  # GET /users/1
  # GET /users/1.json
  def show
    render json: @user if stale?(@user)
  end

  def create
    user = User.create(user_params)
    if user.new_record?
      render json: { errors: user.errors.messages }, status: 422
    else
      render json: user.session_api_key, status: 201
    end
  end

  private

  # Strong Parameters (Rails 4)
  def user_params
    params.require(:user).permit(:name, :password, :password_confirmation)
  end
end