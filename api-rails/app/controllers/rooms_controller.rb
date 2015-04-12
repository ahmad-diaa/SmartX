class RoomsController < ApplicationController
	
	before_action :set_room, only: [:show, :update, :destroy]
  #Returns list of rooms for a specific user. 
  # GET /rooms
  # GET /rooms.json
  def index
    @user = User.find(params[:user_id])
    @rooms = @user.rooms.all
    render json: @rooms if stale?(etag: @rooms.all, last_modified: @rooms.maximum(:updated_at))
  end

  #Returns room with a given room id for a specific user. 
  # GET /rooms/1
  # GET /rooms/1.json
  def show
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:id])
    render json: @room if stale?(@room)
  end
 
  def getName
    @user=User.find(params[:user_id])
    @room=@user.rooms.find(params[:id])
    @name = @room.name
    render json: @name if stale?(@name)
  end

  #Creates room with room parameters for a specific user.
  # POST /rooms
  # POST /rooms.json
   def create
    @user=User.find(params[:user_id])
    @room = @user.rooms.create(room_params)
    if @room.save
      render json: @room, status: :created
    else
      render json: @room.errors, status: :unprocessable_entity
    end
  end

  #Updates room with given room id for specific user.
  # PATCH/PUT /rooms/1
  # PATCH/PUT /rooms/1.json
  def update
    if @room.update(room_params)
      head :no_content
    else
      render json: @room.errors, status: :unprocessable_entity
    end
  end

  
  #Deletes room with given room id for a specific user.
  # DELETE /rooms/1
  # DELETE /rooms/1.json
  def destroy
    @user = User.find(params[:user_id])
    @room = @user.room.find(params[:id])
    @room.destroy
    head :no_content
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_room
    @room = Room.find(params[:id])
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def room_params
    params.require(:room).permit(:name, :user_id)
  end
end
