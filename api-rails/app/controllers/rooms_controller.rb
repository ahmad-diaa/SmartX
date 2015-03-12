class API::V1::RoomsController < ApplicationController
	
	before_action :set_room, only: [:show, :update, :destroy]

  # GET /rooms
  # GET /rooms.json
  def index
    @rooms = Room.all

    render json: @rooms if stale?(etag: @rooms.all, last_modified: @rooms.maximum(:updated_at))
  end

  # GET /rooms/1
  # GET /rooms/1.json
  def show
    render json: @room if stale?(@room)
  end

  # POST /rooms
  # POST /rooms.json
  def create
    @room = Room.new(room_params)

    if @room.save
      render json: @room, status: :created
    else
      render json: @room.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /rooms/1
  # PATCH/PUT /rooms/1.json
  def update
    if @room.update(room_params)
      head :no_content
    else
      render json: @room.errors, status: :unprocessable_entity
    end
  end

  # DELETE /rooms/1
  # DELETE /rooms/1.json
  def destroy
    @room.destroy

    head :no_content
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_room
      @room = Room.find(params[:room_id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def room_params
      params.require(:room).permit(:room_id,:name, :photo)
    end
end
