class DevicesController < ApplicationController
  # GET /devices
  # GET /devices.json
  def index
    @user=User.find(params[:user_id])
    @room = @user.rooms.find(params[:room_room_id])
    @devices = @room.devices.all



    render json: @devices if stale?(etag: @devices.all, last_modified: @devices.maximum(:updated_at))
  end

  # GET /devices/1
  # GET /devices/1.json
  def show
    @user=User.find(params[:user_id])
     @room=@user.rooms.find(params[:room_room_id])
     @device= @room.devices.find(params[:id])
    render json: @device if stale?(@device)
  end

  # POST /devices
  # POST /devices.json
  def create
    
     @user=User.find(params[:user_id])
     @room=@user.rooms.find(params[:room_room_id])
     @device= @room.devices.create(device_params)
    @device.user_id= @room.user_id
    if @device.save
      render json: @device, status: :created
    else
      render json: @device.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /devices/1
  # PATCH/PUT /devices/1.json
  def update
     @user=User.find(params[:user_id])
     @room=@user.rooms.find(params[:room_room_id])
     @device= @room.devices.find(params[:id])
    if @device.update(device_params)
      head :no_content
    else
      render json: @device.errors, status: :unprocessable_entity
    end
  end

  # DELETE /devices/1
  # DELETE /devices/1.json
  def destroy
   @user=User.find(params[:user_id])
     @room=@user.rooms.find(params[:room_room_id])
     @device= @room.devices.find(params[:id])
    @device.destroy
    head :no_content
  end

  private

    # Never trust parameters from the scary internet, only allow the white list through.
    def device_params
      params.require(:device).permit(:name,:user_id,:room_id)
    end
end

