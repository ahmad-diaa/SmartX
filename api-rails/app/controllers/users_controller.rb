class API::V1::UsersController < ApplicationController
  before_action :set_user, only: [:show, :update, :destroy]

  # GET /users
  # GET /users.json
  def index
    @users = User.all

    # Search
    #@users = @users.search(params[:q]) if params[:q]

    # Filter for relationship
    #@users = @users.relationship(params[:relationship]) if params[:relationship]

    # Order by
    #@users = @users.order(params[:order].gsub(':', ' ')) if params[:order]

    # Pagination
    #if (params[:offset] && params[:limit])
    #  @users = @users.page(1).per(params[:limit]).padding(params[:offset])
    #else
    #  @users = @users.page(1).per(25)
    #end


    render json: @users if stale?(etag: @users.all, last_modified: @users.maximum(:updated_at))
  end

  # GET /users/1
  # GET /users/1.json
  def show
    render json: @user if stale?(@user)
  end

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

  # PATCH/PUT /users/1
  # PATCH/PUT /users/1.json
  def update
    if @user.update(user_params)
      head :no_content
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

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
      params.require(:user).permit(:name, :password)
    end
end
