class UsersController < ApplicationController
  before_action :set_user, only: [:show, :update, :destroy]
  #Returns list of users. 
  # GET /users
  # GET /users.json
  def index
    @users = User.all
    render json: @users if stale?(etag: @users.all, last_modified: @users.maximum(:updated_at))
  end

  # GET /user
# GET /user.json
def security
    
@securityQ = User.where( :name => params[:name] )

# @securityQ = @user
    render json: @securityQ.last.securityQ if stale?(@securityQ )
end
  
  #Returns user with a given id. 
  # GET /users/1
  # GET /users/1.json
  def show
    
    render json: @user if stale?(@user)
  end

  #Creates user with given parameters.
  # POST /users
  # POST /users.json
  def create
    @user = User.new(user_params)
    if @user.save
      render json: @user, status: :created
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  #Updates user with given id.
  # PATCH/PUT /users/1
  # PATCH/PUT /users/1.json
  def update
    if @user.update(user_params)
      head :no_content
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  #Deletes user with given id.
  # DELETE /users/1
  # DELETE /users/1.json
  def destroy
    @user.destroy
    head :no_content
  end


  private
  # Use callbacks to share common setup or constraints between actions.
  def set_user
    @user = User.find(params[:id])
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def user_params
    params.require(:user).permit(:name, :password, :email, :phone, :securityQ , :securityA)
  end
end
